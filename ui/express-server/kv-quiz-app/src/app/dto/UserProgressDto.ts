export class UserProgressDto {
  name: string = '';
  progress: number = 0;
  testCompleted: boolean = false;
  answeredQuestions: number = 0;
  correctAnswers: number = 0;
  totalQuestions: number = 0;

  constructor(name: string, progress: number, testCompleted: boolean, answeredQuestions: number, correctAnswers: number, totalQuestions: number) {
    this.name = name;
    this.progress = progress;
    this.testCompleted = testCompleted;
    this.answeredQuestions = answeredQuestions;
    this.correctAnswers = correctAnswers;
    this.totalQuestions = totalQuestions;
  }  
}