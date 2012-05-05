/*
 * This file is part of websocktets-gl - simple WebSocket example
 * Copyright (C) 2012  Burt Parkers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bpark;

import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.websockets.DefaultWebSocket;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketApplication;
import com.sun.grizzly.websockets.WebSocketException;
import com.sun.grizzly.websockets.WebSocketListener;

import java.util.logging.Logger;

/**
 * The echo application receives a message through a websocket connection and sends it back to the client.
 *
 * @author Burt Parkers
 */
public class EchoApplication extends WebSocketApplication {

    /** The logger. */
    private static final Logger logger = Logger.getLogger(EchoApplication.class.getName());

    @Override
    public boolean isApplicationRequest(Request request) {
        final String uri = request.requestURI().toString();
        return uri.endsWith("/echo");
    }

    @Override
    public WebSocket createWebSocket(ProtocolHandler protocolHandler, WebSocketListener... listeners) {
        return new DefaultWebSocket(protocolHandler, listeners);
    }

    @Override
    public void onMessage(WebSocket socket, String text) {
        logger.info("Received message: " + text);
        try {
            socket.send(text);
            logger.info("Sent message: " + text);
        } catch (WebSocketException e) {
            logger.info("Error, closing socket: " + e.getMessage());
            socket.close();
        }
    }
}
