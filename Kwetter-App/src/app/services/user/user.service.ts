import {Injectable} from '@angular/core';
import {User} from '../../models/user';
import {ApiService} from '../api/api.service';

@Injectable()
export class UserService {

    userUrl = "users";

    constructor(private apiService: ApiService) {}

    public getUser(userId: number): any {
        let url: string = this.userUrl + "/" + userId;
        return this.apiService.get<any>(url);
    }

    public getFollowers(userId: number): any {
        let url: string = this.userUrl + "/getFollowers/" + userId;
        return this.apiService.get<any>(url);
    }

    public getFollowing(userId: number): any {
        let url: string = this.userUrl + "/getFollowing/" + userId;
        return this.apiService.get<any>(url);
    }

    public unfollow(userIdFollower: number, userIdFollowing: number): any {
        let url: string = this.userUrl + "/unfollow/"
        return this.apiService.put<any>(
            url,
            {"userIdFollower": userIdFollower, "userIdFollowing": userIdFollowing},
            true
        );
    }

    public follow(userIdFollower: number, userIdFollowing: number): any {
        let url: string = this.userUrl + "/follow/"
        return this.apiService.put<any>(
            url,
            {"userIdFollower": userIdFollower, "userIdFollowing": userIdFollowing},
            true
        );
    }
}
