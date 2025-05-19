import { UserProgressDto } from "./UserProgressDto";

export class TestProgressUpdateEventDto {
    testId: string = '';
    userProgress: UserProgressDto | null = null;

    constructor(testId: string, userProgress: UserProgressDto) {
        this.testId = testId;
        this.userProgress = userProgress;
    }

    toJson(): string {
        return JSON.stringify(this);
    }
}
