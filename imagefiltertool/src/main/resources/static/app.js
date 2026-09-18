document.addEventListener('DOMContentLoaded', () => {
    const dropZone = document.getElementById('dropZone');
    const fileInput = document.getElementById('fileInput');
    const originalPreview = document.getElementById('originalPreview');
    const filteredPreview = document.getElementById('filteredPreview');
    const filterButtons = document.querySelectorAll('.filter-btn');
    const downloadBtn = document.getElementById('downloadBtn');

    let currentFile = null;
    let currentFilter = 'Original';

    dropZone.addEventListener('click', () => fileInput.click());

    fileInput.addEventListener('change', (e) => {
        if (e.target.files.length > 0) {
            handleFile(e.target.files[0]);
        }
    });

    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.style.backgroundColor = '#d5dbdb';
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.style.backgroundColor = '#ecf0f1';
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.style.backgroundColor = '#ecf0f1';
        if (e.dataTransfer.files.length > 0) {
            handleFile(e.dataTransfer.files[0]);
        }
    });

    function handleFile(file) {
        if (!file.type.startsWith('image/')) {
            alert('Veuillez sélectionner un fichier image valide.');
            return;
        }

        currentFile = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            originalPreview.src = e.target.result;
            originalPreview.classList.remove('hidden');
        };
        reader.readAsDataURL(file);

        processFilter();
    }

    filterButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            filterButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            currentFilter = btn.getAttribute('data-filter');

            if (currentFile) {
                processFilter();
            }
        });
    });

    async function processFilter() {
        if (!currentFile) return;

        const formData = new FormData();
        formData.append('file', currentFile);
        formData.append('filter', currentFilter);

        try {
            const response = await fetch('/api/filters/apply', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error('Erreur lors du traitement de l\'image.');
            }

            const data = await response.json();
            
            filteredPreview.src = data.imageBase64;
            filteredPreview.classList.remove('hidden');

            downloadBtn.href = data.imageBase64;
            downloadBtn.classList.remove('hidden');
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue lors de l\'application du filtre.');
        }
    }
});