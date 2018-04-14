import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';

@Injectable()
export class AuthenticationService {

    authenticationUrl = "user";

    constructor(private apiService: ApiService) {}

    public getCurrentUserId(): number {
        //TODO Return proper id and whatever
        console.log("TODO Return proper id during AuthenticationService.getCurrentUserId");
        return 1;
    }

    public signIn(username: string, password: string): any {
        this.apiService.post<any>(this.authenticationUrl + "/signIn", {"username": username, "password": password}, true)
            .subscribe(response => {
                console.log(response["Record"]);
                if (response["Record"] && response["succes"]) {
                    localStorage.setItem("token", response["Record"]);
                }
            },
                error => {
                    console.log(error);
                    console.log("TODO Implement proper error handling..")
                });
    }
}
