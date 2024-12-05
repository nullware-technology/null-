import prisma from "@/lib/db";

export const getUserByEmail = async (email: string) => {
  if (!email) return null;

  try {
    const user = await prisma.user.findUnique({
      where: {
        email,
      },
    });

    return user;
  } catch (error) {
    console.log(error);

    return null;
  }
};