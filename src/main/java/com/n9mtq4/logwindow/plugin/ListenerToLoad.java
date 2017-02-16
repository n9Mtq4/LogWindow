/*
 * NOTE: This is added by intellij IDE. Disregard this copyright if there is another copyright later in the file.
 * Copyright (C) 2016  Will (n9Mtq4) Bresnahan
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.n9mtq4.logwindow.plugin;

/**
 * Created by will on 10/16/16 at 10:53 PM.
 * 
 * @author Will "n9Mtq4" Bresnahan
 */
public class ListenerToLoad {
	
	private String classIdentifier;
	private String[] dependencies;
	private String[] antiDependencies;
	
	private boolean forceLoad = false;
	
	public ListenerToLoad(String classIdentifier, String[] dependencies, String[] antiDependencies) {
		this.classIdentifier = classIdentifier;
		this.dependencies = dependencies;
		this.antiDependencies = antiDependencies;
	}
	
	public boolean shouldLoad() {
		
		// force load
		if (forceLoad) return true;
		
		boolean goodDependencies = true;
		for (String dependency : dependencies) {
			try {
				Class.forName(dependency); // pass, don't do anything
			}catch (ClassNotFoundException e) {
				// fail, set check to false, and exit the loop, no need to do anything else
				goodDependencies = false;
				break;
			}
		}
		
		// if check failed, lets stop it here
		if (!goodDependencies) return false;
		
		// lets check antiDependencies
		boolean goodAntiDependencies = true;
		for (String antiDependecy : antiDependencies) {
			try {
				Class.forName(antiDependecy);
				// fail, set check to false, and exit the loop, no need to finish checking
				goodAntiDependencies = false;
				break;
			}catch (ClassNotFoundException e) {
				// pass, don't do anything
			}
		}
		
		// if check failed, lets stop it here
		if (!goodAntiDependencies) return false;
		
		// all checks are done, so lets return true
		return true;
		
	}
	
	public String getClassIdentifier() {
		return classIdentifier;
	}
	
	public boolean isForceLoad() {
		return forceLoad;
	}
	
}
