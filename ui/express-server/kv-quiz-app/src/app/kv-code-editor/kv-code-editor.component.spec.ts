import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KvCodeEditorComponent } from './kv-code-editor.component';

describe('KvCodeEditorComponent', () => {
  let component: KvCodeEditorComponent;
  let fixture: ComponentFixture<KvCodeEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KvCodeEditorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KvCodeEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
