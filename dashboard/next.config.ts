import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: "standalone",
  env: {
    API_AUTHORIZATION_TOKEN: process.env.API_AUTHORIZATION_TOKEN,
  },
};

export default nextConfig;
