// SOLUZIONE CUSTOM PER AGGIUNGERE HEADER AI FILES

const fs = require('fs');
const path = require('path');

// Header per TypeScript, JavaScript, CSS e SCSS
const headerJs = `/**
 * @license
 *
 * Copyright © ${new Date().getFullYear()} Regione Piemonte
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://interoperable-europe.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 * or
 * https://opensource.org/license/EUPL-1.2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
`;

// Header per file HTML
const headerHtml = `<!--
 * @license
 *
 * Copyright © ${new Date().getFullYear()} Regione Piemonte
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://interoperable-europe.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 * or
 * https://opensource.org/license/EUPL-1.2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 -->
`;

const jsHeaderRegex = /(\/\*\*[\s\S]*?@license[\s\S]*?\*\/\s*)/i;
const htmlHeaderRegex = /(<!--[\s\S]*?@license[\s\S]*?-->\s*)/i;


// Estensioni dei file da processare
const targetExtensions = ['.ts', '.js', '.css', '.scss', '.html'];
const ignoreDirs = ['node_modules', 'dist', '.git', '.angular', 'assets'];

function processDirectory(dir) {
  try {
    const files = fs.readdirSync(dir);

    files.forEach(file => {
      const fullPath = path.join(dir, file);

      try {
        const stat = fs.statSync(fullPath);

        if (stat.isDirectory()) {
          if (!ignoreDirs.includes(file) && !file.startsWith('.')) {
            processDirectory(fullPath);
          }
        } else if (targetExtensions.includes(path.extname(file).toLowerCase())) {
          addOrReplaceHeaderInFile(fullPath);
        }
      } catch (error) {
        console.warn(`⚠️  Impossibile accedere a: ${fullPath}`, error.message);
      }
    });
  } catch (error) {
    console.warn(`⚠️  Impossibile leggere la directory: ${dir}`, error.message);
  }
}

function addOrReplaceHeaderInFile(filePath) {
  try {
    const extension = path.extname(filePath).toLowerCase();
    const allowedExtensions = ['.ts', '.js', '.css', '.scss', '.html'];

    if (!allowedExtensions.includes(extension)) {
      return; // Skip file non supportati
    }

    let content = fs.readFileSync(filePath, 'utf8');
    let newContent;
    let headerAction = 'aggiunto';

    if (extension === '.html') {
      if (htmlHeaderRegex.test(content)) {
        newContent = content.replace(htmlHeaderRegex, headerHtml + '\n');
        headerAction = 'sostituito';
      } else {
        newContent = headerHtml + '\n' + content;
      }
    } else {
      if (jsHeaderRegex.test(content)) {
        newContent = content.replace(jsHeaderRegex, headerJs + '\n');
        headerAction = 'sostituito';
      } else {
        newContent = headerJs + '\n' + content;
      }
    }

    if (content !== newContent) {
      fs.writeFileSync(filePath, newContent);
      console.log(`✅ Header ${headerAction} in: ${filePath}`);
    }

  } catch (error) {
    console.error(`❌ Errore processando il file: ${filePath}`, error.message);
  }
}


// Esegui lo script
console.log('🚀 Aggiunta header ai file sorgenti...');
console.log('📁 Directory processata: ./src');
console.log('📄 Estensioni: .ts, .js, .css, .scss, .html');
console.log('────────────────────────────────────────────');

processDirectory('./src');

console.log('────────────────────────────────────────────');
console.log('✅ Operazione completata!');
