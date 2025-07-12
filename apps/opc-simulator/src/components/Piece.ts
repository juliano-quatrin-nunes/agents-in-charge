export default class Piece {
  id: number;
  x: number;
  y: number;
  counted: boolean;
  size: number;
  isHigh: boolean;

  constructor(id: number, type = "random") {
    this.id = id;
    this.x = 0; // Position on the main conveyor (mm)
    this.y = 0; // Position on the discard conveyor (mm)
    this.counted = false; // Flag to prevent double counting

    this.size = 80; // Diameter in mm

    const normalizedType = type.toLowerCase();

    if (normalizedType === "random") {
      this.isHigh = Math.random() < 0.4; // 40% chance of being high
    } else {
      this.isHigh = normalizedType === "high";
    }
  }
}
