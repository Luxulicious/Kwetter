import {Injectable} from '@angular/core';
import {User} from '../../models/user';
import {ApiService} from '../api/api.service';

@Injectable()
export class UserService {
    
    userUrl = "user";

    constructor(private apiService: ApiService) {}

    public getUser(userId: number): any {
        let url: string = this.userUrl + "/getUser/" + userId
        return this.apiService.get<User>(url);     
    }

}
