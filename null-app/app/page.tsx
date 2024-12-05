import { redirect } from "next/navigation";

import prisma from "@/lib/db";

import { auth } from "@/auth";

import { Navbar } from "@/components/Shared/Navbar";

import { SliderVideo } from "./(routes)/(home)/components/SliderVideo";
import { TrendingMovies } from "./(routes)/(home)/components/TrendingMovies";
import { ListMovies } from "./(routes)/(home)/components/ListMovies";

export default async function Home() {
  const session = await auth();

  if (!session || !session.user || !session.user.id) {
    return redirect("/login");
  }

  const usersNetflix = await prisma.userNetflix.findMany({
    where: {
      userId: session.user.id,
    },
  });

  const movies = await prisma.movie.findMany();
  const trendingMovies = await prisma.popularMovie.findMany({
    orderBy: { ranking: "asc" },
  });

  return (
    <div className="relative bg-zinc-900">
      <Navbar users={usersNetflix} />
      <SliderVideo />
      <TrendingMovies movies={trendingMovies} />
      <ListMovies movies={movies} />
    </div>
  );
}