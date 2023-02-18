const express = require("express");
const cors = require("cors");
const ytdl = require("ytdl-core");
const ffmpeg = require("fluent-ffmpeg");
const readline = require("readline");
const { createReadStream, createWriteStream } = require("fs");

const app = express();

app.use(cors());

app.listen(4000, () => {
  console.log("Server Works! At port 4000");
});

app.get("/download", (req, res) => {
  let URL = req.query.URL;
  let title = req.query.title;
  res.header("Content-Disposition", `attachment; filename="${title}.m4a`);

  let stream = ytdl(URL, { filter: "audioonly" }).pipe(res);

  //   let outputStream = createWriteStream();

  //   let start = Date.now();
  //   ffmpeg(stream)
  //     .audioBitrate(128)
  //     .pipe(outputStream, {end:true})
  //     .on("progress", (p) => {
  //       readline.cursorTo(process.stdout, 0);
  //       process.stdout.write(`${p.targetSize}kb downloaded`);
  //     })
  //     .on("end", () => {
  //       console.log(`\ndone, thanks - ${(Date.now() - start) / 1000}s`);
  //     })
  //     .on("error", function (err) {
  //       console.log("wahala" + err);
  //     });
});

