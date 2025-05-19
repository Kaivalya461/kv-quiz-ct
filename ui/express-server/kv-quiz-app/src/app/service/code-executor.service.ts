import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { McqTestDto } from '../dto/McqTestDto';
import { environment } from '../../environments/environment';
import { CodeExecutorRequestDto } from '../dto/CodeExecutorRequestDto';
import { CodeExecutorResponseDto } from '../dto/CodeExecutorResponseDto';

@Injectable({
  providedIn: 'root'
})
export class CodeExecutorService {
  domainBaseUrl: string = environment.apiBaseUrl;
  // domainBaseUrl: string = 'http://localhost:8080/code-executor/execute';
  url:string = this.domainBaseUrl;

  constructor(private http: HttpClient) { }

  // This function is used for executing User Code from backend API.
  executeCode(requestDto: CodeExecutorRequestDto): Observable<CodeExecutorResponseDto> {
      console.log("Calling executeCode service - " + requestDto.lang);
      return this.http.post<CodeExecutorResponseDto>(this.url + "/code-executor/execute", requestDto)
          .pipe(
              catchError(this.handleError<CodeExecutorResponseDto>(
                  'executeCode()', ))
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
