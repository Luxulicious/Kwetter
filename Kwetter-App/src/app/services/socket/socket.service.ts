import {Injectable} from '@angular/core';



@Injectable()
export class SocketService {

    private socketUrl: string = "ws://localhost:50191/Kwetter/KwetterServerEndPoint/";
    private webSocket: WebSocket = null;

    constructor() {}

    public instantiateSocket(username: string): any {
        if (!this.webSocket) {
            this.webSocket = new WebSocket(this.socketUrl + username);       
        }
        return this.webSocket;
    }
}
