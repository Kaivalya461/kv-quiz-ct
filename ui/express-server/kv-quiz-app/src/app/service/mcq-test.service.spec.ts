import { TestBed } from '@angular/core/testing';

import { McqTestService } from './mcq-test.service';

describe('McqTestService', () => {
  let service: McqTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(McqTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
