import {Injectable} from '@angular/core';
import {ApiService} from '../api/api.service';
import {Post} from '../../models/post';

@Injectable()
export class PostService {

    postUrl: string = "posts";

    constructor(private apiService: ApiService) {}

    getRecentPostsByUser(userId: number, limit: number): any {
        let url: string = this.postUrl + "/getRecentPostsByPoster/" + userId + "/" + limit;
        return this.apiService.get<any>(url);
    }

    createNewPost(userId: number, content: string): any {
        let url: string = this.postUrl + "/createNewPost/"
        return this.apiService.post<any>(url, {"posterId": userId, "content": content}, true);
    }

    getTimeline(userId: number, limit: number): any {
        let url: string = this.postUrl + "/getTimeline/" + userId + "/" + limit;
        return this.apiService.get<any>(url);
    }

    searchPosts(searchCriteria: string): any {
        let url: string = this.postUrl + "/searchPosts/" + searchCriteria;
        return this.apiService.get<any>(url);
    }
    
    
}
