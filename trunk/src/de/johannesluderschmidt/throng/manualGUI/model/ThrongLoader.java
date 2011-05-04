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
package de.johannesluderschmidt.throng.manualGUI.model;


import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.johannesluderschmidt.throng.manualGUI.view.ThrongManualGUI;
import de.johannesluderschmidt.throng.proxy.Config;


public class ThrongLoader {
	private HashMap<String, ThrongClient> clientsDict;
	private Vector<ThrongClient> clientsVector;
	
	public ThrongLoader(String path, ThrongManualGUI app){
		getClientInfos(path);
	}
	
	public Vector<ThrongClient> getClientsVector() {
		return clientsVector;
	}

	private void getClientInfos(String path){
		clientsDict = new HashMap<String, ThrongClient>();
		clientsVector = new Vector<ThrongClient>();
		
	 	try {
	 		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	 		Document doc = builder.parse(new File(path));
	 		
//	 		//receiver property
	 		NodeList outputIpNL = doc.getElementsByTagName("outputIp");
	 		if(outputIpNL.getLength() > 0){
	 			Config.getInstance().setReceiver(outputIpNL.item(0).getAttributes().getNamedItem("ip").getNodeValue());
	 		}
	 		
	 		//output port property
	 		NodeList outputPortNL = doc.getElementsByTagName("outputPort");
	 		if(outputPortNL.getLength() > 0){
	 			Config.getInstance().setReceiverPort(outputPortNL.item(0).getAttributes().getNamedItem("port").getNodeValue());
	 		}
	 		
	 		//useOffsetTopAndBottom property
	 		NodeList useOffsetsNL = doc.getElementsByTagName("useOffsets");
	 		if(useOffsetsNL.getLength() > 0){
	 			Config.getInstance().setUseOffsets(Boolean.parseBoolean(useOffsetsNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
	 		}
	 		
	 		//offsetXTop property
	 		NodeList offsetXTopNL = doc.getElementsByTagName("offsetXLeft");
	 		if(offsetXTopNL.getLength() > 0){
	 			Config.getInstance().setOffsetXLeft(Float.parseFloat(offsetXTopNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
	 		}
	 		
	 		//offsetXBottom property
	 		NodeList offsetXBottomNL = doc.getElementsByTagName("offsetXRight");
	 		if(offsetXBottomNL.getLength() > 0){
	 			Config.getInstance().setOffsetXRight(Float.parseFloat(offsetXBottomNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
	 		}
	 		
	 		//offsetYTop property
	 		NodeList offsetYTopNL = doc.getElementsByTagName("offsetYTop");
	 		if(offsetYTopNL.getLength() > 0){
	 			Config.getInstance().setOffsetYTop(Float.parseFloat(offsetYTopNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
	 		}
	 		
	 		//offsetYBottom property
	 		NodeList offsetYBottomNL = doc.getElementsByTagName("offsetYBottom");
	 		if(offsetYBottomNL.getLength() > 0){
	 			Config.getInstance().setOffsetYBottom(Float.parseFloat(offsetYBottomNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
	 		}
	 		
	 		//flipXAndYproperty
//	 		NodeList flipXAndYNL = doc.getElementsByTagName("flipXAndY");
//	 		if(flipXAndYNL.getLength() > 0){
//	 			Config.getInstance().setFlipXAndY(Boolean.parseBoolean(flipXAndYNL.item(0).getAttributes().getNamedItem("value").getNodeValue()));
//	 		}
	 		
	 		//clients
	 		NodeList clients = doc.getElementsByTagName("client");
	 		for (int i = 0; i<clients.getLength();i++){
	 			Node client = clients.item(i);
	 			client.getNodeName();
	 			NamedNodeMap atts = client.getAttributes();
	 			Node address = atts.getNamedItem("address");
	 			Node port = atts.getNamedItem("port");
	 			Node xPos = atts.getNamedItem("offsetX");
	 			Node yPos = atts.getNamedItem("offsetY");
	 			Node width = atts.getNamedItem("offsetWidth");
	 			Node height = atts.getNamedItem("offsetHeight");
	 			Node overlapXLeft = atts.getNamedItem("overlapXLeft");
	 			Node overlapXRight = atts.getNamedItem("overlapXRight");
	 			Node type = atts.getNamedItem("type");
	 			Node cropStartY = atts.getNamedItem("cropStartY");
	 			Node cropEndY = atts.getNamedItem("cropEndY");
	 			Node flipRotation = atts.getNamedItem("flipRotation");
	 			Node flipXYRotation = atts.getNamedItem("flipXAndY");
	 			
	 			ThrongClient multifloscClient = new ThrongClient();
	 			multifloscClient.setIp(address.getNodeValue());
	 			multifloscClient.setPort(Integer.parseInt(port.getNodeValue()));
	 			multifloscClient.setOffsetX(Float.parseFloat(xPos.getNodeValue()));
	 			multifloscClient.setOffsetY(Float.parseFloat(yPos.getNodeValue()));
	 			multifloscClient.setWidth(Float.parseFloat(width.getNodeValue()));
	 			multifloscClient.setHeight(Float.parseFloat(height.getNodeValue()));
	 			multifloscClient.setOverlapXLeft(Float.parseFloat(overlapXLeft.getNodeValue()));
	 			multifloscClient.setOverlapXRight(Float.parseFloat(overlapXRight.getNodeValue()));
	 			multifloscClient.setType(type.getNodeValue());
	 			multifloscClient.setCropStartY(Float.parseFloat(cropStartY.getNodeValue()));
	 			multifloscClient.setCropEndY(Float.parseFloat(cropEndY.getNodeValue()));
	 			multifloscClient.setFlipRotation(Boolean.parseBoolean(flipRotation.getNodeValue()));
	 			multifloscClient.setFlipXAndY(Boolean.parseBoolean(flipXYRotation.getNodeValue()));
	 			
	 			clientsVector.add(multifloscClient);
	 			clientsDict.put(address.getNodeValue()+port.getNodeValue(), multifloscClient);
	 		}
	 			
		}catch( Exception ex ) {
			ex.printStackTrace();
		}		
	}

	public HashMap<String, ThrongClient> getClientsDict() {
		return clientsDict;
	}
}
