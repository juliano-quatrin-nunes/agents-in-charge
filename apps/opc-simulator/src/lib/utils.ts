import Piece from "../components/Piece";

export const isPieceAtPosition = (
  piece: Piece,
  position: { x: number; y: number }
) => {
  const minPosition = {
    x: position.x - piece.size / 2,
    y: position.y - piece.size / 2,
  };
  const maxPosition = {
    x: position.x + piece.size / 2,
    y: position.y + piece.size / 2,
  };

  return (
    piece.x >= minPosition.x &&
    piece.x < maxPosition.x &&
    piece.y >= minPosition.y &&
    piece.y < maxPosition.y
  );
};

export function toCamelCase(text: string) {
  return text.replace(/(?:^\w|[A-Z]|\b\w)/g, (word, index) => {
    return index === 0 ? word.toLowerCase() : word.toUpperCase();
  }).replace(/\s+/g, '');
}