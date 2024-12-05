import bcrypt from "bcryptjs";
import { NextResponse } from "next/server";
import prisma from "@/lib/db";

import { getUserByEmail } from "@/data/user";

export async function POST(request: Request) {
  console.log("database url" + process.env.DATABASE_URL)
  const { email, password } = await request.json();

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const existingUser = await getUserByEmail(email);

    if (existingUser) {
      return new NextResponse("Email already exists", { status: 400 });
    }

    const userCreated = await prisma.user.create({
      data: {
        email,
        password: hashedPassword,
      },
    });

    return NextResponse.json(userCreated);
  } catch (error) {
    console.log(error);

    return new NextResponse("Internal Error", { status: 500 });
  }
}