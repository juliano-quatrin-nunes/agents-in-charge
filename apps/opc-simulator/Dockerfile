FROM node:18-alpine

WORKDIR /app

# Install pnpm
RUN npm install -g pnpm

# Copy package manifests and install all dependencies
COPY package.json pnpm-lock.yaml ./
RUN pnpm install

# Copy source code and tsconfig
COPY tsconfig.json .
COPY src ./src

# Build TypeScript to JavaScript
RUN pnpm run build

# Prune development dependencies for a smaller image
RUN pnpm prune --prod

# Expose OPC-UA and HTTP ports
EXPOSE 4840
EXPOSE 4041

# Start the simulation server from the compiled output
CMD ["node", "dist/festoServer.js"]