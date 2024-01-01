const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const cors = require('cors');

const app = express();
const server = http.createServer(app);

// Konfiguriere socket.io mit CORS
const io = socketIo(server, {
    cors: {
        origin: "*",  // Erlaube alle Origins
        methods: ["GET", "POST"]
    }
});

const port = 3001;

app.use(cors()); // Aktiviere CORS für alle Express-Routen

// ... Rest des Codes ...

// Aktiviere Parsing von JSON-Body
app.use(express.json());

// HTTP-POST-Endpunkt für /play-video
app.post('/play-video', (req, res) => {
    // Überprüfe, ob der Request-Body und die videoId vorhanden sind
    if (!req.body || !req.body.videoId) {
        console.error("Fehler: Request-Body oder videoId fehlt.");
        return res.status(400).send('Video-ID fehlt im Request-Body');
    }

    const videoId = req.body.videoId;
    console.log(`Video mit ID ${videoId} wird gestartet`);

    // Sende die Video-ID an alle verbundenen WebSocket-Clients
    io.emit('videoId', videoId);

    // Sende eine Bestätigungsantwort
    res.status(200).send(`Video mit ID ${videoId} gestartet`);
});

// Event-Handler für neue WebSocket-Verbindungen
io.on('connection', (socket) => {
    console.log('Neue WebSocket-Verbindung');
});

// Starte den Server
server.listen(port, () => {
    console.log(`Server läuft auf http://localhost:${port}`);
});
