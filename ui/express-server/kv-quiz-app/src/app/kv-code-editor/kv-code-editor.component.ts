import { Component, HostListener, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MonacoEditorModule } from 'ngx-monaco-editor-v2';
import { CodeExecutorService } from '../service/code-executor.service';
import { CodeExecutorRequestDto } from '../dto/CodeExecutorRequestDto';

@Component({
  selector: 'app-kv-code-editor',
  imports: [
    FormsModule,
    MonacoEditorModule
  ],
  templateUrl: './kv-code-editor.component.html',
  styleUrl: './kv-code-editor.component.css',
  providers: []
})
export class KvCodeEditorComponent {
  editorOptions = {theme: 'vs-dark', language: 'java'};
  lang: string = '';
  javascriptDefaultCode: string = "console.log(\"Hello, World!\");\n\nconsole.log(\"Output: \" + reverseString());\n\nfunction reverseString() {\n inputString = \"This is a sentence.\";\n\n // Your code here\n \n\n return inputString;\n}";
  javaDefaultCode: string = "import java.util.*;\n\npublic class Main {\n public static void main(String[] args) {\n System.out.println(\"Hello, World!\");\n \n // Questions: Reverse the inputString\n String inputString = \"All the best!!\";\n \n // Write your code here\n \n \n System.out.println(\"Output -> \" + inputString);\n }\n}";
  code: string = this.javaDefaultCode;

  codeOutput: any = '';

  formattedCode: string = '';

  @Input() selectedLang: any = '';

  constructor(private codeExecutorService: CodeExecutorService) {

  }

  // onInit(editor: any) {
  //   let line = editor.getPosition();
  //   console.log(line);
  // }

  ngOnInit(): void {
    console.log("this.selectedLang -" + this.selectedLang);
    if (this.selectedLang === '') {
      this.selectedLang = 'JAVA'; // default selection
    }

    if (this.selectedLang === 'JAVA') {
      this.code = this.javaDefaultCode;
    } else if (this.selectedLang === 'JS' || this.selectedLang === 'ANGULAR') {
      this.selectedLang = 'JS';
      this.code = this.javascriptDefaultCode;
    }
  }

  executeCode() {
    this.formattedCode = JSON.stringify(this.code);

    // Backend API call
    const requestDto: CodeExecutorRequestDto = {
      lang: this.selectedLang,
      content: this.code,
      fileName: ''
    };
    this.codeExecutorService.executeCode(requestDto).subscribe(response => {
      this.codeOutput = response.output;
    })
  }

  onLangSelectionChange(event: any) {
    const selectedLang = event.target.value;
    if (selectedLang === 'JAVA') {
      this.code = this.javaDefaultCode;
    } else if (selectedLang === 'JS') {
      this.code = this.javascriptDefaultCode;
    }
  }

  @HostListener('document:keydown', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.ctrlKey && event.key === 'Enter') {
      this.executeCode();
    }
  }
}
