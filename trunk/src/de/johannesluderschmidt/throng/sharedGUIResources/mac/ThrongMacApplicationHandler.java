/*
 	This file is part of Throng - Tuio multiplexeR that crOps and Globalizes.
	For more information see http://code.google.com/p/throng
	
	Copyright (c) 2010-2011 Johannes Luderschmidt <ich@johannesluderschidt.de>

    Throng is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Throng is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Throng. If not, see <http://www.gnu.org/licenses/>.
 */
package de.johannesluderschmidt.throng.sharedGUIResources.mac;

import java.util.EventObject;

import com.muchsoft.util.mac.Java14Handler;

public class ThrongMacApplicationHandler implements Java14Handler {

	public void handleAbout(EventObject arg0) {
	}

	public void handleOpenApplication(EventObject arg0) {
	}

	public void handleOpenFile(EventObject arg0, String arg1) {
	}

	public void handlePrefs(EventObject arg0) {
	}

	public void handlePrintFile(EventObject arg0, String arg1) {
	}

	public void handleQuit(EventObject arg0) {
		System.exit(0);
	}

	public void handleReOpenApplication(EventObject arg0) {
	}

}
