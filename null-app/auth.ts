import NextAuth from "next-auth";
import { PrismaAdapter } from "@auth/prisma-adapter";
import prima from "@/lib/db";
import authConfig from "./auth.config";

export const { handlers, signIn, signOut, auth } = NextAuth({
  adapter: PrismaAdapter(prima),
  callbacks: {
    async session({ token, session }) {
      if (token.sub && session.user) {
        session.user.id = token.sub;
      }

      return session;
    },
    async jwt({ token }) {
      return token;
    },
  },
  session: { strategy: "jwt" },
  trustHost: process.env.NODE_ENV === "development",
  ...authConfig,
});