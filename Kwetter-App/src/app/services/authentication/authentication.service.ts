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

    public signIn(email: string, password: string): any {
        this.apiService.post<any>(this.authenticationUrl + "signIn", {"email": email, "password": password}, true)
            .subscribe(response => {
                console.log("TODO Implement token sheninigans here...");
                console.log(response);
            },
                error => {
                    console.log(error);
                    console.log("TODO Implement proper error handling..")
                });
    }
}
