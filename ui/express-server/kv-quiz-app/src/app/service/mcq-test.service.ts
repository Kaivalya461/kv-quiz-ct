import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { McqTestDto } from '../dto/McqTestDto';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class McqTestService {
  // domainBaseUrl: string = "https://kv-quiz-api.kvapps.in";
  domainBaseUrl: string = environment.apiBaseUrl;
  url:string = this.domainBaseUrl;

  constructor(private http: HttpClient) { }

  // This function gets McqTest Details based on TestId from backend.
  getMcqTestDetails(testId: string): Observable<McqTestDto> {
      return this.http.get<McqTestDto>(this.url + "/mcq-test/test-id/" + testId)
          .pipe(
              catchError(this.handleError<McqTestDto>(
                  'getMcqTestDetails', new McqTestDto()))
          );
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
        console.error(error); // log to console instead
        this.log(`${operation} failed: ${error.message}`);
        // Let the app keep running by returning an empty result.
        return of(result as T);
    };
  }
  /** Log a HeroService message with the MessageService */
  private log(message: string) {
      console.log(message);
  }
}
