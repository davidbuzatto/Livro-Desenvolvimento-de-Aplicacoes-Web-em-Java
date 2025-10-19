import { defineConfig } from "vite";
import { viteStaticCopy } from "vite-plugin-static-copy";

export default defineConfig({
    base: "./",
    // desligando o comportamento padrão para a pasta public (vite só para build)
    publicDir: false,
    build: {
        outDir: "dist",
        minify: true,
        emptyOutDir: true,
        rollupOptions: {
            input: "./index.html"
        }
    },
    // fazendo a cópia integralmente (vite só para build)
    plugins: [
        viteStaticCopy({
            targets: [
                {
                    src: "public",
                    dest: "."
                }
            ]
        })
    ]
});