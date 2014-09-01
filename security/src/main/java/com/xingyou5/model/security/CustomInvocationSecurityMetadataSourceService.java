package com.xingyou5.model.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.stereotype.Service;

import com.xingyou5.model.security.dao.mapper.UresourceMapper;
import com.xingyou5.model.security.entity.Uresource;

 

@Service("customSecurityMetadataSource")
public class CustomInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	/**key：url <br>value：roleNames */
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private static Uresource allMenuDetail;
	// private RegexUrlPathMatcher pathMatcher = new RegexUrlPathMatcher();
	private AntUrlPathMatcher pathMatcher = new AntUrlPathMatcher();
	@Autowired
	private UresourceMapper<Uresource> uresourceMapper;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			allAttributes.addAll(entry.getValue());
		}
		return allAttributes;
	}

	// 根据URL  FilterInvocation，找到相关的权限配置。
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		if (resourceMap == null) {
			this.loadResourceRole();
		}
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String resURL = it.next();
			String contextPathResURL = request.getContextPath() + resURL; 
			String requestUrl =  request.getRequestURI();
			if (pathMatcher.pathMatchesUrl(contextPathResURL,  requestUrl.split("\\.")[0])) {
				Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);
				return returnCollection;
			}
		}
		return null;
	}
	
	// 根据URL，找到相关的权限配置。
	public Collection<ConfigAttribute> getAttributes(String url)
			throws IllegalArgumentException {
		if (resourceMap == null) {
			this.loadResourceRole();
		}
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String resURL = it.next();
			if (pathMatcher.pathMatchesUrl(resURL, url)) {
				Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);
				return returnCollection;
			}
		}
		return null;
	}
	@PostConstruct
	public void init(){
		//先加载资源权限
		loadResourceRole();
		//再加载资源菜单
		loadMenuModel();
	}
	//加载资源权限
	private void loadResourceRole() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Uresource> list = uresourceMapper.listUroleIdsResUrl();
		for(Uresource res:list){
			Integer roleId = res.getUroleId();
			String url = res.getUrl();
			
			if(url!=null&&!url.equals("")&&roleId!=null&&!roleId.equals(0)){
				ConfigAttribute ca = new SecurityConfig(String.valueOf(roleId));
				if (resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> atts = resourceMap.get(url);
					atts.add(ca);
					//resourceMap.put(url, atts);
				} else {
					Collection<ConfigAttribute> atts = new HashSet<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}
			}
		}
	}
	//加载资源菜单
	private void loadMenuModel(){
		allMenuDetail  = getMenuModelForSystem(false);
	}
    public static Uresource getAllMenuModel(){
		return allMenuDetail;
	}
    public static Map<String, Collection<ConfigAttribute>> getResourceMap(){
    	return resourceMap;
    }
    
	public boolean supports(Class<?> arg0) {
		return true;
	}

	public CustomInvocationSecurityMetadataSourceService() {
		// loadResourceRole();
	}
	
	
	/**
	 * 获取菜单，及对应的角色        系统启动时自动加载的  
	 * @param onlyMenu true：则只取菜单资源，去除功能性资源    false：取所有资源
	 * @return MenuModel
	 * */
	public Uresource getMenuModelForSystem(boolean onlyMenu) {
		Map<String, Collection<ConfigAttribute>> tempResourceMap = (Map<String, Collection<ConfigAttribute>>)resourceMap;
		Set<Map.Entry<String, Collection<ConfigAttribute>>> iterator = tempResourceMap.entrySet();
		Map<String, Set<Integer>> resourceMap = new HashMap<String, Set<Integer>>();
		for(Map.Entry<String, Collection<ConfigAttribute>> entry:iterator){
			Set<Integer> set = new HashSet<Integer>();
			for(ConfigAttribute c:entry.getValue()){
				set.add(Integer.parseInt(c.toString()));
			}
			resourceMap.put(entry.getKey(), set);
		}
		//Map<String, Set<String>> resourceMap = getResourceRole();
		List<Uresource> list = uresourceMapper.listNormalResources();
		Map<Integer,Uresource> menuMap = new HashMap<Integer,Uresource>();
		for(Uresource m:list){
			menuMap.put(m.getUresourceId(), m);
		}
		Uresource menus = new Uresource();
		menuMap.put(0, menus);
		//设置根菜单
		menus.setIsMenu(1);
		menus.setUresourceName("根菜单");
		for(Uresource m:list){
			if(!onlyMenu||m.getIsMenu()!=null&&m.getIsMenu().equals(1)){
				Integer parentId = m.getParentId();
				List<Uresource> childMenuModel = null;
				if(parentId!=null&&!parentId.equals(0)){
					if(menuMap.get(parentId)!=null){
						childMenuModel = menuMap.get(parentId).getChildMenuModel();
					}
				}else{
					parentId = 0;
					childMenuModel = menus.getChildMenuModel();
				}
				if(childMenuModel==null){
					childMenuModel = new ArrayList<Uresource>();
				}
				if(m.getUrl()!=null&&!m.getUrl().trim().equals("")){
					m.setRoles(resourceMap.get(m.getUrl()));
				}
				childMenuModel.add(m);
				CompareMenuModel comparator=new CompareMenuModel();
			    Collections.sort(childMenuModel, comparator);
			    if(menuMap.get(parentId)!=null){
				   menuMap.get(parentId).setChildMenuModel(childMenuModel);
			    }
			}
		}
		return menus;
	}
	

}