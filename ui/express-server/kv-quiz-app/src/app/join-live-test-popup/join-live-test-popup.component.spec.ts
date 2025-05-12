import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinLiveTestPopupComponent } from './join-live-test-popup.component';

describe('JoinLiveTestPopupComponent', () => {
  let component: JoinLiveTestPopupComponent;
  let fixture: ComponentFixture<JoinLiveTestPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JoinLiveTestPopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JoinLiveTestPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
