export interface Question {
  id: number;
  seqNum: number;
  questionText: string;
  options: string[];
  correctAnswer: string;
  category: string;
  difficulty: string;
}
