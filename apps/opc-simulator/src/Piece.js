class Piece {
    constructor(id, type = 'random') {
        this.id = id;
        this.x = 0; // Position on the main conveyor (mm)
        this.y = 0; // Position on the discard conveyor (mm)
        this.counted = false; // Flag to prevent double counting
        
        if (type === 'random') {
            this.isHigh = Math.random() < 0.4; // 40% chance of being high
        } else {
            this.isHigh = type === 'high';
        }
    }
}

module.exports = Piece; 