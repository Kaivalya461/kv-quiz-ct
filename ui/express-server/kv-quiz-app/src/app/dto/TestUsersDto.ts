import { UserProgressDto } from "./UserProgressDto";

export class TestUsersDto {
  testId: string = '';
  users: UserProgressDto[] = [];

  constructor(testId: string, users: UserProgressDto[]) {
    this.testId = testId;
    this.users = users;
  }

  toJson(): string {
    return JSON.stringify(this);
  }
}