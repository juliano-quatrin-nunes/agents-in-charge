"use client";

import { Button } from "./ui/Button";
import { WithTooltip } from "./WithTooltip";

const SIMULATOR_URI =
  process.env.NEXT_PUBLIC_SIMULATOR_URI || "http://localhost:4041";

const addPiece = (height: "high" | "low" | "random") => {
  try {
    fetch(`${SIMULATOR_URI}/add-piece`, {
      method: "POST",
      body: JSON.stringify({ height }),
    });
  } catch (error) {
    console.error(error);
  }
};

export const AddPiece = () => {
  return (
    <div className="flex flex-col gap-4 justify-center w-full h-full items-center text-center">
      <h2 className="text-lg font-semibold">Add Piece</h2>
      <div className="flex flex-row gap-4">
        <WithTooltip content="Adds a high piece to the simulation.">
          <Button onClick={() => addPiece("high")}>Add High Piece</Button>
        </WithTooltip>
        <WithTooltip content="Adds a low piece to the simulation.">
          <Button onClick={() => addPiece("low")}>Add Low Piece</Button>
        </WithTooltip>
        <WithTooltip content="Adds a piece with a random height (40% chance of being high).">
          <Button onClick={() => addPiece("random")}>Add Random Piece</Button>
        </WithTooltip>
      </div>
    </div>
  );
};
