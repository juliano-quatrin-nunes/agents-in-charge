FROM node:18-alpine

WORKDIR /app

RUN npm install -g pnpm

# Copy package.json and lockfile
COPY package.json pnpm-lock.yaml ./
RUN pnpm install --frozen-lockfile

# Copy server files
COPY festo-server.js ./
COPY src ./src

# Expose OPC-UA and HTTP ports
EXPOSE 4840
EXPOSE 4041

# Start the simulation server
CMD ["pnpm", "start"]