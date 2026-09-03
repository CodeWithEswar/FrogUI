import React, { useEffect, useRef } from 'react';

interface VercelRaycastHeroProps {
  className?: string;
}

interface Obstacle {
  x: number;
  y: number;
  radius: number;
}

export const VercelRaycastHero: React.FC<VercelRaycastHeroProps> = ({ className = '' }) => {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const mouseRef = useRef<{ x: number; y: number; isHovering: boolean }>({
    x: 0,
    y: 0,
    isHovering: false
  });

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;

    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    let animationFrameId: number;
    let width = (canvas.width = canvas.parentElement?.clientWidth || 900);
    let height = (canvas.height = canvas.parentElement?.clientHeight || 450);

    const handleResize = () => {
      if (!canvas || !canvas.parentElement) return;
      width = canvas.width = canvas.parentElement.clientWidth;
      height = canvas.height = canvas.parentElement.clientHeight;
    };

    window.addEventListener('resize', handleResize);

    const handleMouseMove = (e: MouseEvent) => {
      const rect = canvas.getBoundingClientRect();
      mouseRef.current.x = e.clientX - rect.left;
      mouseRef.current.y = e.clientY - rect.top;
      mouseRef.current.isHovering = true;
    };

    const handleMouseLeave = () => {
      mouseRef.current.isHovering = false;
    };

    const parent = canvas.parentElement;
    parent?.addEventListener('mousemove', handleMouseMove);
    parent?.addEventListener('mouseleave', handleMouseLeave);

    // Light source state
    let lightX = width * 0.52;
    let lightY = height * 0.52;
    let time = 0;

    const render = () => {
      time += 0.02;

      // Organic autonomous figure-8 orbit
      const targetAutoX = width * 0.52 + Math.cos(time * 0.8) * (width * 0.08) + Math.sin(time * 0.4) * 20;
      const targetAutoY = height * 0.52 + Math.sin(time * 1.1) * (height * 0.12);

      // Smooth interpolation (lerp) towards mouse or auto target
      const targetX = mouseRef.current.isHovering ? mouseRef.current.x : targetAutoX;
      const targetY = mouseRef.current.isHovering ? mouseRef.current.y : targetAutoY;

      lightX += (targetX - lightX) * 0.08;
      lightY += (targetY - lightY) * 0.08;

      // Clear with deep dark base
      ctx.clearRect(0, 0, width, height);

      // Define obstacles relative to center (exactly like Vercel's cluster)
      const centerX = width * 0.56;
      const centerY = height * 0.52;

      const obstacles: Obstacle[] = [
        { x: centerX + 20, y: centerY - 55, radius: 14 },
        { x: centerX + 75, y: centerY - 25, radius: 16 },
        { x: centerX + 50, y: centerY + 45, radius: 15 },
        { x: centerX - 15, y: centerY + 65, radius: 12 },
        { x: centerX + 110, y: centerY + 25, radius: 13 }
      ];

      // 1. Draw subtle background grid boxes
      const gridSize = 36;
      ctx.strokeStyle = 'rgba(255, 255, 255, 0.04)';
      ctx.lineWidth = 1;

      ctx.beginPath();
      for (let x = 0; x < width; x += gridSize) {
        ctx.moveTo(x, 0);
        ctx.lineTo(x, height);
      }
      for (let y = 0; y < height; y += gridSize) {
        ctx.moveTo(0, y);
        ctx.lineTo(width, y);
      }
      ctx.stroke();

      // Grid intersection dots
      ctx.fillStyle = 'rgba(255, 255, 255, 0.08)';
      for (let x = 0; x < width; x += gridSize) {
        for (let y = 0; y < height; y += gridSize) {
          ctx.beginPath();
          ctx.arc(x, y, 1, 0, Math.PI * 2);
          ctx.fill();
        }
      }

      // 2. Draw Radiant Point Light Field (Vercel warm radial illumination)
      const lightRadius = Math.max(width, height) * 0.75;
      const lightGradient = ctx.createRadialGradient(
        lightX,
        lightY,
        0,
        lightX,
        lightY,
        lightRadius
      );

      // Warm amber/crimson/monochrome gradient aura as seen in Vercel's hero
      lightGradient.addColorStop(0, 'rgba(255, 255, 255, 1)');
      lightGradient.addColorStop(0.03, 'rgba(255, 235, 220, 0.95)');
      lightGradient.addColorStop(0.08, 'rgba(240, 140, 90, 0.45)');
      lightGradient.addColorStop(0.2, 'rgba(180, 45, 30, 0.22)');
      lightGradient.addColorStop(0.45, 'rgba(70, 15, 15, 0.08)');
      lightGradient.addColorStop(1, 'rgba(0, 0, 0, 0)');

      ctx.save();
      ctx.fillStyle = lightGradient;
      ctx.fillRect(0, 0, width, height);
      ctx.restore();

      // 3. Cast Volumetric Directional Shadows from each obstacle
      const shadowLength = Math.max(width, height) * 1.5;

      obstacles.forEach(obs => {
        const dx = obs.x - lightX;
        const dy = obs.y - lightY;
        const dist = Math.hypot(dx, dy);

        if (dist <= obs.radius) return; // Light inside obstacle

        const baseAngle = Math.atan2(dy, dx);
        const spread = Math.asin(Math.min(1, obs.radius / dist));

        // Tangent points on obstacle circumference
        const angle1 = baseAngle - spread;
        const angle2 = baseAngle + spread;

        const p1x = obs.x - Math.sin(angle1) * obs.radius;
        const p1y = obs.y + Math.cos(angle1) * obs.radius;

        const p2x = obs.x + Math.sin(angle2) * obs.radius;
        const p2y = obs.y - Math.cos(angle2) * obs.radius;

        // Far shadow projections
        const f1x = p1x + Math.cos(angle1) * shadowLength;
        const f1y = p1y + Math.sin(angle1) * shadowLength;

        const f2x = p2x + Math.cos(angle2) * shadowLength;
        const f2y = p2y + Math.sin(angle2) * shadowLength;

        // Shadow polygon with soft edge falloff
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(p1x, p1y);
        ctx.lineTo(f1x, f1y);
        ctx.lineTo(f2x, f2y);
        ctx.lineTo(p2x, p2y);
        ctx.closePath();

        // Deep shadow fill with soft gradient falloff
        const shadowGrad = ctx.createLinearGradient(obs.x, obs.y, (f1x + f2x) / 2, (f1y + f2y) / 2);
        shadowGrad.addColorStop(0, 'rgba(4, 4, 6, 0.96)');
        shadowGrad.addColorStop(0.3, 'rgba(5, 5, 8, 0.92)');
        shadowGrad.addColorStop(1, 'rgba(7, 7, 10, 0.85)');

        ctx.fillStyle = shadowGrad;
        ctx.fill();
        ctx.restore();
      });

      // 4. Draw Obstacle Pillars (3D cylinder look with light-facing highlight)
      obstacles.forEach(obs => {
        const dx = lightX - obs.x;
        const dy = lightY - obs.y;
        const angle = Math.atan2(dy, dx);

        // Body base
        ctx.save();
        ctx.beginPath();
        ctx.arc(obs.x, obs.y, obs.radius, 0, Math.PI * 2);
        ctx.fillStyle = '#0a0a0c';
        ctx.fill();
        ctx.lineWidth = 1.5;
        ctx.strokeStyle = '#18181b';
        ctx.stroke();

        // Rim light reflection on edge facing the light
        ctx.beginPath();
        ctx.arc(obs.x, obs.y, obs.radius, angle - Math.PI * 0.4, angle + Math.PI * 0.4);
        ctx.strokeStyle = 'rgba(255, 255, 255, 0.35)';
        ctx.lineWidth = 2;
        ctx.stroke();
        ctx.restore();
      });

      // 5. Draw the Glowing Light Source Indicator (Vercel radiant center orb)
      ctx.save();

      // Outer ambient glow ring
      const orbGlow = ctx.createRadialGradient(lightX, lightY, 0, lightX, lightY, 28);
      orbGlow.addColorStop(0, 'rgba(255, 255, 255, 1)');
      orbGlow.addColorStop(0.3, 'rgba(255, 240, 225, 0.9)');
      orbGlow.addColorStop(0.7, 'rgba(255, 140, 90, 0.4)');
      orbGlow.addColorStop(1, 'rgba(255, 100, 50, 0)');

      ctx.fillStyle = orbGlow;
      ctx.beginPath();
      ctx.arc(lightX, lightY, 28, 0, Math.PI * 2);
      ctx.fill();

      // Solid bright white core
      ctx.fillStyle = '#ffffff';
      ctx.beginPath();
      ctx.arc(lightX, lightY, 7.5, 0, Math.PI * 2);
      ctx.shadowColor = '#ffffff';
      ctx.shadowBlur = 16;
      ctx.fill();

      ctx.restore();

      animationFrameId = requestAnimationFrame(render);
    };

    render();

    return () => {
      cancelAnimationFrame(animationFrameId);
      window.removeEventListener('resize', handleResize);
      parent?.removeEventListener('mousemove', handleMouseMove);
      parent?.removeEventListener('mouseleave', handleMouseLeave);
    };
  }, []);

  return (
    <canvas
      ref={canvasRef}
      className={`absolute inset-0 w-full h-full pointer-events-none select-none z-0 ${className}`}
      aria-hidden="true"
    />
  );
};
