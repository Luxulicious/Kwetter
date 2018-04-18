import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {UserToken} from '../../models/userToken';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable()
export class AuthenticationService {


    private tokenKey: string = "token";
    private userIdKey: string = "userId";
    private authenticationUrl = "user";

    constructor(private apiService: ApiService, private router: Router) {}

    public getSignedInUserId(): number {
        //TODO Return proper id and whatever
        //return 1;
        return Number(localStorage.getItem('userId'));;
    }

    public signOut() {
        localStorage.removeItem(this.tokenKey);
        localStorage.removeItem(this.userIdKey);
        this.redirectToSignIn();
    }

    public signIn(username: string, password: string): any {
        this.apiService.post<any>(this.authenticationUrl + "/signIn", {"username": username, "password": password}, true)
            .subscribe(response => {
                console.log(response["Record"]);
                if (response["Record"] && response["succes"]) {
                    let userToken: UserToken = response["Record"];
                    localStorage.setItem(this.tokenKey, userToken.token);
                    localStorage.setItem(this.userIdKey, (userToken.userId).toString());
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

    //TODO Move this to another service for routing and extract the route to a field
    public redirectToHome() {
        this.router.navigate(["home"]);
    }

    //TODO Move this to another service for routing and extract the route to a field
    public redirectToSignIn() {
        this.router.navigate(["auth/sign-in"]);
    }

    isSignedIn() {
        if (this.getSignedInUserId())
            return true;
        else {
            return false;
        }
    }
}
