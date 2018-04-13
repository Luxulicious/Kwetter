import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';



@Injectable()
export class ApiService {

    private baseUrl: string = "http://localhost:50191/Kwetter/api/";

    constructor(private httpClient: HttpClient) {}

    public apiGetRequest<T>(path: string): Observable<T> {
        let url = this.baseUrl + path;
        return this.httpClient.get<T>(url);
    }
}
