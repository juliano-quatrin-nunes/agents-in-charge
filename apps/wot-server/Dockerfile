FROM nodered/node-red

# Switch to root to install pnpm globally
USER root
RUN npm install -g pnpm

# Switch back to node-red user for security
USER node-red

# Set working directory to /data (Node-RED's user directory)
WORKDIR /data

# Copy package.json and pnpm-lock.yaml for deterministic installs
COPY --chown=node-red:root package.json pnpm-lock.yaml ./

# Install dependencies using the lock file for deterministic builds
# --frozen-lockfile ensures exact versions from lock file (like npm ci)
# --prod installs only production dependencies (important for Node-RED contrib modules)
RUN pnpm install --frozen-lockfile --prod && pnpm store prune

# Copy Node-RED configuration files
COPY --chown=node-red:root settings.js flows_cred.json flows.json ./

# Verify modules are installed and discoverable
RUN pnpm list --depth=0

# Switch back to Node-RED working directory
WORKDIR /usr/src/node-red