import { ComponentFixture, TestBed } from '@angular/core/testing';

import { McqTestComponent } from './mcq-test.component';

describe('McqTestComponent', () => {
  let component: McqTestComponent;
  let fixture: ComponentFixture<McqTestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [McqTestComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(McqTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
