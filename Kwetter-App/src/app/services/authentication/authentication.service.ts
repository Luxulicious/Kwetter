import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {UserToken} from '../../models/userToken';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable()
export class AuthenticationService {

    authenticationUrl = "user";

    constructor(private apiService: ApiService, private router: Router) {}

    public getCurrentUserId(): number {
        //TODO Return proper id and whatever
        //return 1;
        return Number(localStorage.getItem('userId'));;
    }

    public signIn(username: string, password: string): any {
        this.apiService.post<any>(this.authenticationUrl + "/signIn", {"username": username, "password": password}, true)
            .subscribe(response => {
                console.log(response["Record"]);
                if (response["Record"] && response["succes"]) {
                    let userToken: UserToken = response["Record"];
                    localStorage.setItem("token", userToken.token);
                    localStorage.setItem("userId", (userToken.userId).toString());
                    this.redirectToHome();
                }
                else if (!response["success"]) {
                    for (let msg in response["messages"]) {
                        alert(msg.toString());
                    }
                }
            },
                error => {
                    console.log(error);
                    alert("Invalid username and/or password. \n(Or the server might be down)")
                });
    }

    redirectToHome() {
        this.router.navigate(['home']);
    }
}
