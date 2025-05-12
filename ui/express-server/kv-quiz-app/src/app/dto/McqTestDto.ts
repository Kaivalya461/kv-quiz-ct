import { Question } from "./QuestionDto";

export class McqTestDto {
  id: string = '';
  testName: string = ''; // Example: Test001-10thMay2025-11:00AM
  questions: Question[] = [];
  durationInMinutes: number = 0;
  difficultyLevel: string = ''; // easy, intermediate, advanced
  category: string = ''; // java, angular
  createdAt: Date | undefined;
  scheduledAt: Date | undefined;
  scheduleBy: string = ''; // "Admin" if scheduled for future, logged-in username if started manually
}