import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';


@Injectable()
export class ApiService {

    private baseUrl: string = "http://localhost:50191/Kwetter/api/";

    constructor(private httpClient: HttpClient) {}

    public get<T>(path: string, requiresAuthenticationToken?: boolean): Observable<T> {
        let headers: HttpHeaders = new HttpHeaders()
        if (requiresAuthenticationToken) {
            headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        }

        let url = this.baseUrl + path;
        return this.httpClient.get<T>(url);
    }

    public post<T>(postPlain: string, content: any, isJson: boolean, requiresAuthenticationToken?: boolean): Observable<T> {
        let headers: HttpHeaders = new HttpHeaders()
            .append("Content-type", "application/json");
        if (requiresAuthenticationToken) {
            headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        }

        let url = this.baseUrl + postPlain;
        if (!isJson) {
            return this.httpClient.post<T>(url, content, {headers: headers});
        } else {
            return this.httpClient.post<T>(url, JSON.stringify(content), {headers: headers});
        }
    }

    public put<T>(putPlain: string, content: any, isJson: boolean, requiresAuthenticationToken?: boolean): Observable<T> {
        let headers: HttpHeaders = new HttpHeaders()
            .append("Content-type", "application/json");
        if (requiresAuthenticationToken) {
            headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
        }

        let url = this.baseUrl + putPlain;
        if (!isJson) {
            return this.httpClient.put<T>(url, content, {headers: headers});
        } else {
            return this.httpClient.put<T>(url, JSON.stringify(content), {headers: headers});
        }
    }

}
