import { Question } from "./QuestionDto";

export class TestQuestionDto {
  id: string | null = null;
  testId: string | null = null;
  questions: Question[] = [];
}