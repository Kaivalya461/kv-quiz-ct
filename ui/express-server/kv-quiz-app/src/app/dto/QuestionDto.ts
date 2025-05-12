export interface Question {
  id: number;
  questionText: string;
  options: string[];
  correctAnswer: string;
  category: string;
  difficulty: string;
}
