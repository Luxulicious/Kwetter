import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';



@Injectable()
export class ApiService {

    private baseUrl: string = "http://localhost:50191/Kwetter/api/";

    constructor(private httpClient: HttpClient) {}

    public get<T>(path: string): Observable<T> {
        let url = this.baseUrl + path;
        return this.httpClient.get<T>(url);
    }

    public post<T>(postPlain: string, content: any, isJson: boolean): Observable<T> {
        //TODO Append token here

        let headers: HttpHeaders = new HttpHeaders()
            .append("Content-type", "application/json");

        let url = this.baseUrl + postPlain;
        if (!isJson) {
            return this.httpClient.post<T>(url, content, {headers: headers});
        } else {
            return this.httpClient.post<T>(url, JSON.stringify(content), {headers: headers});
        }
    }

}
