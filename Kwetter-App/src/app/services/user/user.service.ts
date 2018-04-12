import {Injectable} from '@angular/core';
import {User} from '../../models/user';

@Injectable()
export class UserService {

    constructor() {}

    public fetchUser(): User {
        //TODO Return proper user and whatever
        console.log("TODO Return proper user during UserService.fetchUser");
        let user: User = new User();
        user.username = "Luxulicious";
        user.bio = "I'm Lux.";
        user.location = "Veghel";
        user.website = "google.com";
        user.icon = "Luxulicious.png"
        return user;
    }

}
