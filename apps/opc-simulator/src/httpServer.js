const express = require("express");
const cors = require("cors");
const Piece = require("./Piece");
const { logEvent } = require('./Logger');

function startHttpServer(simulation) {
    const app = express();
    const httpServerPort = 4041;

    app.use(cors());
    app.use(express.json());

    app.post("/add-piece", (req, res) => {
        const height = req.body.height || 'random'; // 'normal', 'high', or random
        const piece = new Piece(simulation.pieceIdCounter++, height);
        simulation.pieces.push(piece);
        
        const details = `(ID: ${piece.id}, High: ${piece.isHigh})`;
        logEvent('System', 'Piece Added', 'NEW', details);

        res.status(200).json({
            message: "Piece added successfully",
            pieceId: piece.id,
            isHigh: piece.isHigh
        });
    });

    app.listen(httpServerPort, '0.0.0.0', () => {
        console.log(`📡 HTTP server listening on http://0.0.0.0:${httpServerPort}`);
        console.log("✅ POST /add-piece endpoint is ready.");
    });

    return app;
}

module.exports = startHttpServer; 