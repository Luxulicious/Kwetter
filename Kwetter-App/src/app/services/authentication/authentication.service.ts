import {Injectable} from '@angular/core';

@Injectable()
export class AuthenticationService {

    constructor() {}

    public getCurrentUserId(): number {
        //TODO Return proper id and whatever
        console.log("TODO Return proper id during AuthenticationService.getCurrentUserId");
        return 1;
    }
}
