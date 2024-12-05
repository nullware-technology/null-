import { PrismaClient } from '@prisma/client'

const prismaClientSingleton = () => {
  return new PrismaClient(
    {
      datasources: { db: {url: process.env.DATABASE_URL_PRISMA }}
    }
  )
}

declare const globalThis: {
  prismaGlobal: ReturnType<typeof prismaClientSingleton>;
} & typeof global;

const prisma = prismaClientSingleton()

export default prisma

if (process.env.NODE_ENV !== 'production') globalThis.prismaGlobal = prisma