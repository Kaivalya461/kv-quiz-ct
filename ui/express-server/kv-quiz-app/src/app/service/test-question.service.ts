import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { TestQuestionDto } from '../dto/TestQuestionDto';

@Injectable({
  providedIn: 'root'
})
export class TestQuestionService {
  // domainBaseUrl: string = "https://kv-quiz-api.kvapps.in";
  domainBaseUrl: string = "http://localhost:8080";
  url:string = this.domainBaseUrl;

  constructor(private http: HttpClient) { }

  // This function gets McqTest Details based on TestId from backend.
  getQuestionsForTest(testId: string, testCategory: string): Observable<TestQuestionDto> {
      return this.http.get<TestQuestionDto>(this.url + "/test-question/test-id/" + testId + "/test-category/" + testCategory)
          .pipe(
              catchError(this.handleError<TestQuestionDto>(
                  'getQuestionsForTest', new TestQuestionDto()))
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
