const express = require('express');
const path = require('path');

const app = express();
const PORT = 5000;

app.use(express.json());

app.use((req, res, next) => {
  res.header('Cache-Control', 'no-cache, no-store, must-revalidate');
  res.header('Pragma', 'no-cache');
  res.header('Expires', '0');
  next();
});

app.use(express.static(path.join(__dirname, 'public')));

app.get('/api/health', (req, res) => {
  res.json({
    status: 'UP',
    service: 'streamflix-admin-frontend',
    timestamp: new Date().toISOString()
  });
});

app.use((req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.listen(PORT, '0.0.0.0', () => {
  console.log(`Streamflix Admin Panel running on http://0.0.0.0:${PORT}`);
});
