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

$(document).ready(function() {

    var webSocket;

    jQuery.init = function () {
        webSocket = new WebSocket("ws://localhost:8080/echo-websocket/echo");

        webSocket.onopen = function(event) {
            var $textarea = $('#echoLogId');
            $textarea.val($textarea.val() + "Connected.\n");
        };

        webSocket.onmessage = function(event) {
            var $textarea = $('#echoLogId');
            $textarea.val($textarea.val() + "Received: " + event.data + "\n");
            $textarea.animate({scrollTop:$textarea.height()}, 1000);
        };

        webSocket.onclose = function(event) {
            webSocket.send("Disconnected");
        };

        webSocket.onerror = function(event) {
            var $textarea = $('#echoLogId');
            $textarea.val($textarea.val() + "Communication error!\n");
        }
    };

    jQuery.sendEchoMessage = function() {
        var message = $('#echoMessageId').val();
        webSocket.send(message);
        var $textarea = $('#echoLogId');
        $textarea.val($textarea.val() + "Sending: " + message + "\n");
    };

    $.init();
});